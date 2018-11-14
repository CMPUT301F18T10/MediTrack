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

	public void EditContactInfo(String email, String phoneNumber)
	{
		new_contactInfo.setEmail(email);
		new_contactInfo.setPhoneNumber(phoneNumber);
        DataRepositorySingleton.getUser("").setContactInfo(new_contactInfo);
	}
	
	public void addPatient(String patientUserId)
	{
	    DataRepositorySingleton.getUser().AddPatientId(patientUserId);
	}


	public void addBodyLocationImage(Patient patient, ImageView image)
	{
	    //missing part: upload image to database
		String imageId = String.valueOf(image.getTag());
        DataRepositorySingleton.getUser("").getBodyLocationImages().add(imageId);
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
        BodyImages = DataRepositorySingleton.getUser("").getBodyLocationImages();
        for(int i = 0; i<BodyImages.size();i++){
            if(BodyImages.get(i).equals(ImageId)){
                BodyImages.remove(i);
            }
        }
        DataRepositorySingleton.getUser("").setBodyLocationImageIds(BodyImages);
	}
}
