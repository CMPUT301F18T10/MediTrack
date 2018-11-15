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
	private ArrayList<String> patientIds;

	public void EditContactInfo(String email, String phoneNumber)
	{
		new_contactInfo.setEmail(email);
		new_contactInfo.setPhoneNumber(phoneNumber);
		MockDataRepositoryUserManager.GetPatient().setContactInfo(new_contactInfo);
	}

	public void addPatient(String patientUserId)
	{
		patientIds = MockDataRepositoryUserManager.GetCareProvider().getPatientIds();
		patientIds.add(patientUserId);
		MockDataRepositoryUserManager.GetCareProvider().setPatientIds(patientIds);

	}


	public void addBodyLocationImage(ImageView image)
	{
		//missing part: upload image to database
		String imageId = String.valueOf(image.getTag());
		MockDataRepositoryUserManager.GetPatient().getBodyLocationImages().add(imageId);
	}

	public int checkBodyImageNumber()
	{
		BodyImages = MockDataRepositoryUserManager.GetPatient().getBodyLocationImages();
		return BodyImages.size();
	}
	public void deleteBodyLocationImage(String ImageId)
	{
		//missing part: delete image from database
		BodyImages = MockDataRepositoryUserManager.GetPatient().getBodyLocationImages();
		for(int i = 0; i<BodyImages.size();i++){
			if(BodyImages.get(i).equals(ImageId)){
				BodyImages.remove(i);
			}
		}
		MockDataRepositoryUserManager.GetPatient().setBodyLocationImageIds(BodyImages);
	}
}
