package com.example.repository;

import com.example.model.Ordem;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class OrdemRepositoryTest {

    @InjectMock
    OrdemRepository ordemRepository;

    @Test
    public void testListAll() {
        Ordem primeiraOrdem = new Ordem();
        Ordem segundaOrdem = new Ordem();

        List<Ordem> ordens = new ArrayList<>();

        ordens.add(primeiraOrdem);
        ordens.add(segundaOrdem);

        Mockito
            .when(this.ordemRepository.listAll())
            .thenReturn(ordens);

        Assertions.assertSame(segundaOrdem, this.ordemRepository.listAll().get(1));
    }

}
