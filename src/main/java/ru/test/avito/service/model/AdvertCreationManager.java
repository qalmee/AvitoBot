package ru.test.avito.service.model;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.test.avito.domain.Advert;
import ru.test.avito.domain.AdvertInProgress;
import ru.test.avito.domain.UserEntity;
import ru.test.avito.repository.AdvertInProgressRepository;
import ru.test.avito.repository.AdvertRepository;
import ru.test.avito.repository.UserRepository;

import java.util.List;

@Component
@Transactional
public class AdvertCreationManager {

    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;
    private final AdvertInProgressRepository advertInProgressRepository;

    public AdvertCreationManager(AdvertRepository advertRepository, UserRepository userRepository,
                                 AdvertInProgressRepository advertInProgressRepository) {
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
        this.advertInProgressRepository = advertInProgressRepository;
    }

    public void createAnAdvert(String text, UserEntity host) {
        AdvertInProgress newAdvert = new AdvertInProgress();
        newAdvert.setText(text);
        newAdvert.setHost(host);
        advertInProgressRepository.save(newAdvert);
    }

    public void addPhotoToAdvert(String photo, UserEntity host) {
        AdvertInProgress advert = advertInProgressRepository.getByHost(host);
        advert.addPhoto(photo);
        advertInProgressRepository.save(advert);
    }

    public void advertFinished(UserEntity host) {
        AdvertInProgress advertInProgress = advertInProgressRepository.getByHost(host);
        Advert advert = new Advert(advertInProgress);
        if (advertInProgress.getAdvertId() != null
                && advertRepository.existsById(advertInProgress.getAdvertId())) {
            advertRepository.deleteById(advertInProgress.getAdvertId());
        }
        Long oldId = advert.getId();
        advert = advertRepository.save(advert);
        if (oldId != null) {
            if (host.getSavedAdvertIds().remove(oldId)) {
                host.getSavedAdvertIds().add(advert.getId());
            }
            userRepository.saveAndFlush(host);

            List<UserEntity> users = userRepository.findBySavedAdvertIdsContaining(oldId);
            if (users != null && !users.isEmpty()) {
                for (UserEntity user : users) {
                    user.getSavedAdvertIds().remove(oldId);
                    user.getSavedAdvertIds().add(advert.getId());
                }
                userRepository.saveAll(users);
            }
        }
        advertInProgressRepository.delete(advertInProgress);
    }

    public void abortAdvert(UserEntity host) {
        if (advertInProgressRepository.existsByHost(host)) {
            advertInProgressRepository.deleteByHost(host);
        }
    }

    public void startEditAdvert(Advert advert) {
        if (advertInProgressRepository.existsByHost(advert.getHost())) {
            advertInProgressRepository.deleteByHost(advert.getHost());
        }
        AdvertInProgress advertInProgress = new AdvertInProgress(advert);
        advertInProgressRepository.save(advertInProgress);
    }

    public void editText(String text, UserEntity host) {
        AdvertInProgress advertInProgress = advertInProgressRepository.getByHost(host);
        advertInProgress.setText(text);
        advertInProgressRepository.save(advertInProgress);
    }

    public void deletePhotosFromAdvert(UserEntity host) {
        AdvertInProgress advertInProgress = advertInProgressRepository.getByHost(host);
        if (advertInProgress.getPhotos() != null) {
            advertInProgress.getPhotos().clear();
        }
        advertInProgressRepository.save(advertInProgress);
    }

    public void discardChanges(UserEntity host) {
        if (advertInProgressRepository.existsByHost(host)) {
            advertInProgressRepository.deleteByHost(host);
        }
    }


}
