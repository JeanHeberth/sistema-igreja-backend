package com.igreja.adapters.web.resource.support.base;

import com.igreja.adapters.web.resource.support.dataBase.TestDatabaseCleaner;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;

/**
 * Classe base para testes de integração.
 * Garante isolamento limpando o banco antes de cada teste.
 */
public abstract class BaseIntegrationTest {

    @Inject
    TestDatabaseCleaner testDatabaseCleaner;

    @BeforeEach
    void limparBancoAntesDeCadaTeste() {
        testDatabaseCleaner.limparBanco();
    }
}