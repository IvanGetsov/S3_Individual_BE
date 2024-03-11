package individual.individual_backend.controller;

import individual.individual_backend.business.AdvertManager;
import individual.individual_backend.controller.converters.AdvertConverter;
import individual.individual_backend.controller.dto.CreateAdvertRequest;
import individual.individual_backend.controller.dto.CreateAdvertResponse;
import individual.individual_backend.controller.dto.GetAdvertsResposne;
import individual.individual_backend.controller.dto.UpdateAdvertRequest;
import individual.individual_backend.domain.Advert;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/adverts")
@AllArgsConstructor
public class AdvertsController {
    private final AdvertManager advertManager;
    @RolesAllowed({"CAR_OWNER"})
    @PostMapping
    public ResponseEntity<CreateAdvertResponse> createAdvert(@RequestBody @Valid CreateAdvertRequest request) {
        Advert advert = AdvertConverter.convertCreateRequestToDomain(request);
        Integer advertId = advertManager.createAdvert(advert);
        CreateAdvertResponse response = CreateAdvertResponse.builder()
                .id(advertId)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{advertId}")
    public ResponseEntity<Advert> getAdvertById(@PathVariable int advertId) {
        Advert advert = advertManager.getAdvert(advertId);

        if (advert != null) {
            return ResponseEntity.ok(advert);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<GetAdvertsResposne> getAdverts() {
        GetAdvertsResposne response = GetAdvertsResposne.builder()
                .adverts(advertManager.getAllAdverts())
                .build();
        return ResponseEntity.ok(response);
    }
    @RolesAllowed({"CAR_OWNER"})
    @DeleteMapping("/{advertId}")
    public ResponseEntity<Void> deleteAdvert(@PathVariable int advertId) {
        advertManager.deleteAdvert(advertId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAdvert(@PathVariable("id") Integer id,
                                             @RequestBody @Valid UpdateAdvertRequest request) {
        request.setId(id);

        Advert updatedAdvert = AdvertConverter.convertUpdateRequestToDomain(request);
        advertManager.updateAdvert(updatedAdvert);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-brand/{brand}")
    public ResponseEntity<Advert[]> getCarsByBrand(@PathVariable("brand") final String brand) {
        Advert[] adverts = advertManager.findByCarBrand(brand).toArray(Advert[]::new);
        return ResponseEntity.ok(adverts);
    }
    @RolesAllowed({"CAR_OWNER"})
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Advert>> getAdvertsByUserId(@PathVariable("userId") Integer userId) {
        List<Advert> adverts = advertManager.findByUserId(userId);

        if (adverts == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(adverts);
    }

    @PutMapping("/{id}/update-picture")
    public ResponseEntity<Void> updatePicture(@PathVariable("id") Integer id,
                                                     @RequestBody String picture) {
        advertManager.saveCarPicture(id, picture);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/average-price/{brand}")
    public ResponseEntity<Double> getAveragePriceByBrand(@PathVariable("brand") String brand) {
        Double averagePrice = advertManager.getAveragePriceByBrand(brand);

        if (averagePrice != null) {
            return ResponseEntity.ok(averagePrice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


