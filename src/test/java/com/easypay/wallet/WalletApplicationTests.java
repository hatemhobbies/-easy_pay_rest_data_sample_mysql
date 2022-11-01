package com.easypay.wallet;

import com.easypay.wallet.model.Wallet;
import com.easypay.wallet.repository.WalletRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WalletApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WalletRepository walletRepository;

    @Test
    @SneakyThrows
    void getAll() {

        mockMvc.perform(get("/wallets"))
               .andExpect(status().isOk())
               .andDo(print());  // print response to log
    }

    @Test
    @SneakyThrows
    void create() {

        List<Wallet> deviceTypes = walletRepository.findAll();
        assertThat(deviceTypes).isEmpty();

        mockMvc.perform(post("/wallets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{ \"name\":\"Johns Wallet\"}"))
               .andExpect(status().isCreated())
               .andDo(print());  // print response to log

        // verify
        deviceTypes = walletRepository.findAll();
        assertThat(deviceTypes).hasSize(1);
        Wallet deviceType0 = deviceTypes.get(0);
        assertThat(deviceType0.getName()).isEqualTo("Johns Wallet");


        mockMvc.perform(get("/wallets"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.wallets").isArray())
               .andExpect(jsonPath("_embedded.wallets", hasSize(1)))
               .andExpect(jsonPath("_embedded.wallets[0].name").value("Johns Wallet"))
               .andDo(print());  // print response to log
    }

}
