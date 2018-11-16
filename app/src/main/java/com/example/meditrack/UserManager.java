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
	private Patient patient = DataRepositorySingleton.GetPatient();
	private CareProvider careProvider = DataRepositorySingleton.GetCareProvider();

	public void EditContactInfo(Patient patient,String email, String phoneNumber)
	{
		new_contactInfo.setEmail(email);
		new_contactInfo.setPhoneNumber(phoneNumber);
		patient.setContactInfo(new_contactInfo);
	}

	public void addPatient(CareProvider careProvider,String patientUserId)
	{
		patientIds = careProvider.getPatientIds();
		patientIds.add(patientUserId);
        careProvider.setPatientIds(patientIds);

	}


	public void addBodyLocationImage(Patient patient, ImageView image)
	{
		//missing part: upload image to database
		String imageId = String.valueOf(image.getTag());
		patient.getBodyLocationImages().add(imageId);
	}

	public int checkBodyImageNumber(Patient patient)
	{
		BodyImages = patient.getBodyLocationImages();
		return BodyImages.size();
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
		patient.setBodyLocationImageIds(BodyImages);
	}
}
