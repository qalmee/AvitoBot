package ru.test.avito.service.model;

import org.springframework.stereotype.Component;
import ru.test.avito.domain.Advert;
import ru.test.avito.domain.AdvertInProgress;
import ru.test.avito.repository.AdvertInProgressRepository;
import ru.test.avito.repository.AdvertRepository;

@Component
public class AdvertManager {

    private AdvertRepository advertRepository;
    private AdvertInProgressRepository advertInProgressRepository;

    public AdvertManager(AdvertRepository advertRepository, AdvertInProgressRepository advertInProgressRepository) {
        this.advertRepository = advertRepository;
        this.advertInProgressRepository = advertInProgressRepository;
    }

    public void createAnAdvert(String text, Integer hostId) {
        AdvertInProgress newAdvert = new AdvertInProgress();
        newAdvert.setText(text);
        newAdvert.setHostId(hostId);
        advertInProgressRepository.save(newAdvert);
    }

    public void addPhotoToAdvert(String photo, Integer hostId) {
        AdvertInProgress advert = advertInProgressRepository.getByHostId(hostId);
        advert.addPhoto(photo);
    }

    public void advertFinished(Integer hostId) {
        AdvertInProgress advertInProgress = advertInProgressRepository.getByHostId(hostId);
        Advert advert = new Advert(advertInProgress);
        advertRepository.save(advert);
        advertInProgressRepository.save(advertInProgress);
    }

    public void abortAdvert(Integer hostId) {
        advertInProgressRepository.deleteByHostId(hostId);
    }
}
