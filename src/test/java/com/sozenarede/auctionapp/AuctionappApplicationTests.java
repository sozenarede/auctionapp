package com.sozenarede.auctionapp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
public class AuctionappApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getStart() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/start/")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
    
	@Test
	public void sendBid() throws Exception {
		// Starting the instance
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/start/"));

		// Sending less the zero bid value
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/sendbid?bidvalue=-0.1123456&bidowner=jose")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());

		// Send Valid bid value
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/sendbid?bidvalue=0.1123456&bidowner=jose")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());

		// Send Valid bid value with different bid owner and values
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/sendbid?bidvalue=0.13&bidowner=linda")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());

		// Send Valid bid value with different bid owner but repeated value
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/sendbid?bidvalue=0.13&bidowner=sofia")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());

		// Send Valid bid value with same bid owner but different value
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/sendbid?bidvalue=0.012344&bidowner=sofia")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}

	@Test
	public void getEnd() throws Exception {
		// Starting the instance
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/start/"));

		// Send Valid bid value
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/sendbid?bidvalue=0.1123456&bidowner=jose")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());

		// Send Valid bid value with different bid owner and values
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/sendbid?bidvalue=0.13&bidowner=linda")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());

		// Send Valid bid value with different bid owner but repeated value
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/sendbid?bidvalue=0.13&bidowner=sofia")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());

		// Send Valid bid value with same bid owner but different value
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/sendbid?bidvalue=0.012344&bidowner=sofia")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
		
		// End
		mvc.perform(MockMvcRequestBuilders.get("/api/auction/end/")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
}
