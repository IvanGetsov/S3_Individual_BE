    package individual.individual_backend.business.impl;

    import individual.individual_backend.business.AdvertManager;
    import individual.individual_backend.business.converters.AdvertsConverter;
    import individual.individual_backend.business.converters.CarsConverter;
    import individual.individual_backend.controller.converters.AdvertConverter;
    import individual.individual_backend.domain.Advert;
    import individual.individual_backend.domain.Car;
    import individual.individual_backend.persistence.AdvertRepository;
    import individual.individual_backend.persistence.entity.AdvertEntity;
    import individual.individual_backend.persistence.entity.CarEntity;
    import individual.individual_backend.persistence.entity.UserEntity;
import java.util.Collections;
    import lombok.AllArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    @AllArgsConstructor
    @Service
    public class AdvertManagerImpl implements AdvertManager {
    private AdvertRepository repository;
        @Override
        public Integer createAdvert(Advert advert) {
            AdvertEntity advertEntity = AdvertsConverter.toEntity(advert);
            AdvertEntity savedAdvert = repository.save(advertEntity);
            return savedAdvert.getId();
        }

        @Override
        public List<Advert> getAllAdverts() {
            return repository.findAll().stream().map(AdvertsConverter::toDomain).toList();
        }



        @Override
        public Advert getAdvert(Integer id) {
            Optional<AdvertEntity> advertEntityOptional = repository.findById(id);

            if (advertEntityOptional.isPresent()) {
                AdvertEntity advertEntity = advertEntityOptional.get();
                return AdvertsConverter.toDomain(advertEntity);
            } else {
                return null;
            }
        }

        @Override
        public void deleteAdvert(Integer id) {
            repository.deleteById(id);
        }

        @Override
        public void updateAdvert(Advert updatedAdvert) {
            Optional<AdvertEntity> advertEntityOptional = repository.findById(updatedAdvert.getId());

            if (advertEntityOptional.isPresent()) {
                AdvertEntity advertToUpdate = advertEntityOptional.get();
                updateAdvertFields(advertToUpdate, updatedAdvert);
                repository.save(advertToUpdate);
            }
        }

        @Override
        public List<Advert> findByCarBrand(String brand) {
            return repository.findByCar_Brand(brand).stream().map(AdvertsConverter::toDomain).toList();
        }

        @Override
        public List<Advert> findByUserId(Integer userId) {
            if(userId != 0){
                return repository.findByUserId(userId).stream().map(AdvertsConverter::toDomain).toList();
            }
                return Collections.emptyList();
        }

        @Override
        public void saveCarPicture(Integer advertId, String carPicture) {
            AdvertEntity advertEntity = repository.findById(advertId).orElse(null);

            if (advertEntity != null) {
                advertEntity.setPicture(carPicture);
                repository.saveAndFlush(advertEntity);
            }
        }

        @Override
        public Double getAveragePriceByBrand(String brand) {
            List<Advert> advertsByBrand = findByCarBrand(brand);

            if (advertsByBrand != null && !advertsByBrand.isEmpty()) {
                double totalPrices = advertsByBrand.stream()
                        .mapToDouble(Advert::getPricePerDay)
                        .sum();

                return totalPrices / advertsByBrand.size();
            }

            return null;
        }



        private void updateAdvertFields(AdvertEntity existingAdvert, Advert newAdvert) {
            existingAdvert.setAvailableFrom(newAdvert.getAvailableFrom());
            existingAdvert.setMaximumAvailableDays(newAdvert.getMaximumAvailableDays());
            existingAdvert.setPricePerDay(newAdvert.getPricePerDay());
            existingAdvert.setCar(CarsConverter.toEntity(newAdvert.getCar()));
        }
    }
