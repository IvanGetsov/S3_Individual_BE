package individual.individual_backend.controller.dto;

import individual.individual_backend.domain.Advert;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAdvertsResposne {
    private List<Advert> adverts;
}
