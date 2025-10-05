package in.thinkedge.service.Impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import in.thinkedge.model.Transcations;
import in.thinkedge.repository.TranscationRepository;
import in.thinkedge.service.TranscationsService;
import jakarta.annotation.PostConstruct;

@Service
public class TranscationServiceImpl implements TranscationsService {
	@Autowired
	private TranscationRepository repository;
	@Value("${razorpay.key.id}")
	private String razorpayId;
	@Value("${razorpay.key.secret}")
	private String razorpaySecret;
	
	private RazorpayClient razorpayClient;
	@PostConstruct
	public void init() throws RazorpayException {
		this.razorpayClient=new RazorpayClient(razorpayId, razorpaySecret);
	}
	@Override
	public Transcations createTranscations(Transcations transcations) throws RazorpayException {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("amount", transcations.getAmount()*100);
		jsonObject.put("currency", "INR");
		jsonObject.put("receipt", transcations.getEmail());
	Order razorpaOrder=razorpayClient.orders.create(jsonObject);
	transcations.setRazorpayId(razorpaOrder.get("id"));
	transcations.setTransStatus(razorpaOrder.get("status"));
	transcations.setTransDate(Date.valueOf(LocalDate.now()));
//	if(transcations.getTransStatus().equals("PAYMENT DONE"))
		return repository.save(transcations);
		
//	return null;	
	}
	
	public Transcations updateStatus(Map<String, String> map) {
		String razorpayId=map.get("razorpay_order_id");
		Transcations transcations=repository.findByRazorpayId(razorpayId);
		transcations.setTransStatus("PAYMENT DONE");
		return repository.save(transcations);
	}
	
	

}
