package ru.red_collar.service;

import lombok.SneakyThrows;
import ru.red_collar.dao.FilmDao;
import ru.red_collar.dto.CreateFilmDto;
import ru.red_collar.dto.FilmDto;
import ru.red_collar.dto.UpdateFilmDto;
import ru.red_collar.entity.FilmEntity;
import ru.red_collar.entity.Order;
import ru.red_collar.exception.ValidationException;
import ru.red_collar.mapper.CreateFilmMapper;
import ru.red_collar.mapper.FilmMapper;
import ru.red_collar.mapper.UpdateFilmMapper;
import ru.red_collar.util.ComparatorByDuration;
import ru.red_collar.validator.CreateFilmValidator;
import ru.red_collar.validator.StringValidator;
import ru.red_collar.validator.UpdateFilmValidator;
import ru.red_collar.validator.ValidationResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FilmService {

    private static final FilmService INSTANCE = new FilmService();

    private final FilmDao filmDao;

    private final FilmMapper filmMapper;

    private final StringValidator stringValidator;

    private final CreateFilmValidator createFilmValidator;

    private final CreateFilmMapper createFilmMapper;

    private final ImageService imageService;

    private final UpdateFilmMapper updateFilmMapper;

    private final UpdateFilmValidator updateFilmValidator;

    public static FilmService getInstance() {
        return INSTANCE;
    }

    public FilmService() {
        this(FilmDao.getInstance(),
                FilmMapper.getInstance(),
                StringValidator.getInstance(),
                CreateFilmValidator.getInstance(),
                CreateFilmMapper.getInstance(),
                ImageService.getInstance(),
                UpdateFilmMapper.getInstance(),
                UpdateFilmValidator.getInstance());
    }

    public FilmService(FilmDao filmDao,
                       FilmMapper filmMapper,
                       StringValidator stringValidator,
                       CreateFilmValidator createFilmValidator,
                       CreateFilmMapper createFilmMapper,
                       ImageService imageService,
                       UpdateFilmMapper updateFilmMapper,
                       UpdateFilmValidator updateFilmValidator) {
        this.filmDao = filmDao;
        this.filmMapper = filmMapper;
        this.stringValidator = stringValidator;
        this.createFilmValidator = createFilmValidator;
        this.createFilmMapper = createFilmMapper;
        this.imageService = imageService;
        this.updateFilmMapper = updateFilmMapper;
        this.updateFilmValidator = updateFilmValidator;
    }

    public List<FilmDto> findByNameFilm(final String filmName) {
        return filmDao.findByNameFilm(filmName).stream().map(filmMapper::map).toList();
    }


    public List<FilmDto> findAll() {
        return filmDao.findAll().stream().map(filmMapper::map).toList();
    }

    public List<FilmDto> findAll(final Order order) {
        return filmDao.findAll().stream().map(filmMapper::map)
                .sorted(new ComparatorByDuration(order))
                .toList();
    }


    public Optional<FilmDto> findById(final Integer filmId) {
        return filmDao.findById(filmId).map(filmMapper::map);
    }

    public List<FilmDto> findByGenre(final String nameGenre) {
        return filmDao.findByGenre(nameGenre).stream().map(filmMapper::map).toList();
    }

    public List<FilmDto> findByYear(final Integer year) {
        return filmDao.findByYear(year).stream().map(filmMapper::map).toList();
    }

    public List<FilmDto> findByActorName(final String actor) {
        if (actor == null) {
            return List.of();
        }
        final ValidationResult validationResult = stringValidator.isValid(actor);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        return filmDao.findByActorName(actor).stream().map(filmMapper::map).toList();
    }

    @SneakyThrows
    public Integer create(final CreateFilmDto createFilmDto) {
        final ValidationResult validationResult = createFilmValidator.isValid(createFilmDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        final FilmEntity filmEntity = createFilmMapper.map(createFilmDto);
        imageService.upload(filmEntity.getImage(), createFilmDto.getImage().getInputStream());
        return filmDao.save(filmEntity).getId();
    }

    public boolean delete(final Integer filmId) {
        return filmDao.delete(filmId);
    }

    @SneakyThrows
    public void update(final UpdateFilmDto updateFilmDto) {
        final ValidationResult validationResult = updateFilmValidator.isValid(updateFilmDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        final FilmEntity filmEntity = updateFilmMapper.map(updateFilmDto);
        imageService.upload(filmEntity.getImage(), updateFilmDto.getImage().getInputStream());
        filmDao.update(filmEntity);
    }

    public List<FilmDto> concat(final Order order, final Collection<FilmDto>... collection) {
        return Arrays.stream(collection)
                .flatMap(Collection::stream)
                .sorted(new ComparatorByDuration(order))
                .distinct()
                .toList();
    }
}
