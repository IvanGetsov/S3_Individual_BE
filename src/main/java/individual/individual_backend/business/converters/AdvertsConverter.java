package individual.individual_backend.business.converters;


import individual.individual_backend.domain.Advert;
import individual.individual_backend.domain.Car;
import individual.individual_backend.domain.User;
import individual.individual_backend.persistence.entity.AdvertEntity;


public class AdvertsConverter {
    private AdvertsConverter() {
    }

    public static AdvertEntity toEntity(Advert advert) {
        return AdvertEntity.builder()
                .id(advert.getId())
                .user(UsersConvertor.toEntity(advert.getCarOwner()))
                .car(CarsConverter.toEntity(advert.getCar()))
                .availableFrom(advert.getAvailableFrom())
                .maximumAvailableDays(advert.getMaximumAvailableDays())
                .pricePerDay(advert.getPricePerDay())
                .picture(advert.getPicture())
                .build();
    }

    public static Advert toDomain(AdvertEntity advertEntity) {
        Advert advert = new Advert();
        advert.setId(advertEntity.getId());
        User user = UsersConvertor.toDomain(advertEntity.getUser());
        advert.setCarOwner(user);
        Car car = CarsConverter.toDomain(advertEntity.getCar());
        advert.setCar(car);
        advert.setAvailableFrom(advertEntity.getAvailableFrom());
        advert.setMaximumAvailableDays(advertEntity.getMaximumAvailableDays());
        advert.setPricePerDay(advertEntity.getPricePerDay());
        advert.setPicture(advertEntity.getPicture());
        return advert;
    }
}
