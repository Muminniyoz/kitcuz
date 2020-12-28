package uz.kitc.service.mapper;


import uz.kitc.domain.*;
import uz.kitc.service.dto.FaqDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Faq} and its DTO {@link FaqDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FaqMapper extends EntityMapper<FaqDTO, Faq> {



    default Faq fromId(Long id) {
        if (id == null) {
            return null;
        }
        Faq faq = new Faq();
        faq.setId(id);
        return faq;
    }
}
