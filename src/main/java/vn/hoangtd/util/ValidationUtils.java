package vn.hoangtd.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * Created by hoangtd on 1/23/2017.
 */
public class ValidationUtils {
    public static ObjectNode getErrors(BindingResult bindingResult) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        ObjectNode objectNode = objectMapper.createObjectNode();
        for (ObjectError error : bindingResult.getAllErrors()) {
            objectNode.put(error.getCode(), error.getDefaultMessage());
        }
        return objectNode;
    }
}
