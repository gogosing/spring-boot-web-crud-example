package me.gogosing.service.impl;

import me.gogosing.model.Album;
import me.gogosing.model.Song;
import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.AlbumRepository;
import me.gogosing.persistence.repository.SongRepository;
import me.gogosing.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JinBum Jeong on 2020/02/06.
 */
@Service
public class SearchServiceImpl implements SearchService {

    private final AlbumRepository albumRepository;

    private final SongRepository songRepository;

    public SearchServiceImpl(
        AlbumRepository albumRepository,
        SongRepository songRepository
    ) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }

    @Override
    public List<Album> getAlbumList(String title, String locale) {
        return albumRepository.getAlbumEntities(title, locale).stream().map(albumEntity -> {
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

    @Override
    public List<Song> getSongList(String title, String locale) {
        return songRepository.getSongEntities(title, locale).stream().map(songEntity -> {
            final Song song = new Song();
            song.setId(songEntity.getId());
            song.setTitle(songEntity.getTitle());
            song.setTrack(songEntity.getTrackNo());
            song.setLength(songEntity.getLength());
            return song;
        }).collect(Collectors.toList());
    }
}
