package me.gogosing.service.impl;

import me.gogosing.component.PagesComponent;
import me.gogosing.consts.ApplicationConstants;
import me.gogosing.model.Album;
import me.gogosing.model.Pages;
import me.gogosing.model.Pagination;
import me.gogosing.model.Song;
import me.gogosing.persistence.entity.AlbumEntity;
import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.AlbumRepository;
import me.gogosing.service.AlbumService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JinBum Jeong on 2020/02/07.
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    private final String albumPaginationPath;

    private final PagesComponent pagesComponent;

    private final AlbumRepository albumRepository;

    public AlbumServiceImpl(
        @Value("${article.pagination.album:/}")
        String albumPaginationPath,
        PagesComponent pagesComponent,
        AlbumRepository albumRepository
    ) {
        this.albumPaginationPath = albumPaginationPath;
        this.pagesComponent = pagesComponent;
        this.albumRepository = albumRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination getAlbumPagination(int page, int size, String locale) {
        final Page<AlbumEntity> paginatedResult = albumRepository.getPaginatedAlbumEntities(
                locale,
                PageRequest.of((page - 1), (size > 0) ? size : ApplicationConstants.DEFAULT_PAGE_LIMIT)
        );

        final long totalElements = paginatedResult.getTotalElements();
        final Pages pages = pagesComponent.getPages(albumPaginationPath, paginatedResult);

        List<Album> albumList = Collections.emptyList();
        if (totalElements > 0) {
            albumList = paginatedResult.getContent().stream().map(albumEntity -> {
                final Album album = new Album();
                album.setId(albumEntity.getId());
                album.setTitle(albumEntity.getTitle());
                album.setSongs(albumEntity.getSongEntities().stream()
                        .sorted(Comparator.comparingInt(SongEntity::getTrackNo))
                        .map(songEntity -> {
                            final Song song = new Song();
                            song.setId(songEntity.getId());
                            song.setTitle(songEntity.getTitle());
                            song.setTrack(songEntity.getTrackNo());
                            song.setLength(songEntity.getLength());
                            return song;
                        }).collect(Collectors.toList()));
                return album;
            }).collect(Collectors.toList());
        }

        final Pagination pagination = new Pagination();
        pagination.setPages(pages);
        pagination.setAlbums(albumList);

        return pagination;
    }
}
