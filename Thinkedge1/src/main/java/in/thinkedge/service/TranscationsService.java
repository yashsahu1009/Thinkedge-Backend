package in.thinkedge.service;

import com.razorpay.RazorpayException;

import in.thinkedge.model.Transcations;

public interface TranscationsService {
    
	public Transcations createTranscations(Transcations transcations) throws RazorpayException;
}
