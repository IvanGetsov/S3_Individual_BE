package individual.individual_backend.controller.converters;

import individual.individual_backend.controller.dto.CreateAdvertRequest;
import individual.individual_backend.controller.dto.UpdateAdvertRequest;
import individual.individual_backend.domain.Advert;

public class AdvertConverter {
    private AdvertConverter(){

    }

    public static Advert convertCreateRequestToDomain(CreateAdvertRequest request) {
        return Advert.builder()
                .carOwner(request.getCarOwner())
                .car(request.getCar())
                .availableFrom(request.getAvailableFrom())
                .maximumAvailableDays(request.getMaximumAvailableDays())
                .pricePerDay(request.getPricePerDay())
                .picture(request.getPicture())
                .build();
    }

    public static Advert convertUpdateRequestToDomain(UpdateAdvertRequest request) {
        return Advert.builder()
                .id(request.getId())
                .carOwner(request.getCarOwner())
                .car(request.getCar())
                .availableFrom(request.getAvailableFrom())
                .maximumAvailableDays(request.getMaximumAvailableDays())
                .pricePerDay(request.getPricePerDay())
                .picture(request.getPicture())
                .build();
    }
}
