package ues.occ.proyeccion.social.ws.app.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;


public class CarnetValidatorTest{


    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidatorGivenProperValue(){
        var bean = new TestBean("zh15002");
        var violations = validator.validate(bean);
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void testValidatorGivenWrongFormat(){
        var bean = new TestBean("zhx5002"); // three letters
        var violations = validator.validate(bean);
        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void testValidatorInList(){
        var carnets = List.of("zh15002", "zhx5002", "abcde12");
        var bean = new TestBeanWithList(carnets);
        var violations = validator.validate(bean);
        Assertions.assertEquals(2, violations.size());
    }
}

class TestBean{

    @CarnetValidator
    String str1;

    public TestBean(String str1){
        this.str1 = str1;
    }
}

class TestBeanWithList{
    List<@CarnetValidator String> carnets;

    public TestBeanWithList(List<String> carnets){
        this.carnets = carnets;
    }
}
