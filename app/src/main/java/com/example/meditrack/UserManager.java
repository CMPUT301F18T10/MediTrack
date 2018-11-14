package com.example.meditrack;
import android.widget.ImageView;

import java.util.ArrayList;

public class UserManager {
	/* This class is responsible for operating on contact informations and patient
       This would include Edit contactInfo, AddPatient to patient list, 
       and Add comment
     */
	private ContactInfo new_contactInfo;
	private ArrayList<String> BodyImages;
	private Patient patient;

	public void EditContactInfo(Patient patient, String email, String phoneNumber)
	{
		new_contactInfo.setEmail(email);
		new_contactInfo.setPhoneNumber(phoneNumber);
		patient.setContactInfo(new_contactInfo);
	}
	
	public void addPatient(CareProvider careProvider, String patientUserId)
	{
		careProvider.AddPatientId(patientUserId);
	}


	public void addBodyLocationImage(Patient patient, ImageView image)
	{
	    //missing part: upload image to database
		BodyImages = patient.getBodyLocationImages();
		String imageId = String.valueOf(image.getTag());
		BodyImages.add(imageId);
	}
	
	public Boolean checkBodyImageNumber(Patient patient)
	{
		BodyImages = patient.getBodyLocationImages();
		if(BodyImages.size() >= 2){
			return Boolean.TRUE;
		}
		else{
			return Boolean.FALSE;
		}
	}
	public void deleteBodyLocationImage(Patient patient, String ImageId)
	{
		//missing part: delete image from database
        BodyImages = patient.getBodyLocationImages();
        for(int i = 0; i<BodyImages.size();i++){
            if(BodyImages.get(i).equals(ImageId)){
                BodyImages.remove(i);
            }
        }
	}
}
