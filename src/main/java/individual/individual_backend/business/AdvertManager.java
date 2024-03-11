package individual.individual_backend.business;

import individual.individual_backend.domain.Advert;

import java.util.List;

public interface AdvertManager {
    Integer createAdvert(Advert advert);
    List<Advert> getAllAdverts();
    Advert getAdvert(Integer id);
    void deleteAdvert(Integer id);
    void updateAdvert(Advert advert);
    List<Advert> findByCarBrand(String brand);
    List<Advert> findByUserId(Integer userId);
    void saveCarPicture(Integer advertId, String carPicture);
    Double getAveragePriceByBrand(String brand);

}
