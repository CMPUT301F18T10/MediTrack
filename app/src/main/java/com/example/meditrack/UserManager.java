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
	protected Patient patient;
	protected CareProvider careProvider;
	private DataRepositorySingleton mDRS = DataRepositorySingleton.GetInstance();

	private static UserManager user = null;
	public UserManager() throws DataRepositorySingleton.InvalidUserMode, DataRepositorySingleton.DataRepositorySingletonNotInitialized {
		this.patient = mDRS.GetPatient();
		this.careProvider = mDRS.GetCareProvider();

	}
	public void EditContactInfo(UserManager user,String email, String phoneNumber)
	{
		new_contactInfo.setEmail(email);
		new_contactInfo.setPhoneNumber(phoneNumber);
		user.patient.setContactInfo(new_contactInfo);
	}

	public void addPatient(UserManager user,String patientUserId)
	{
		patientIds = user.careProvider.getPatientIds();
		patientIds.add(patientUserId);
		user.careProvider.setPatientIds(patientIds);

	}


	public void addBodyLocationImage(UserManager user, ImageView image)
	{
		//missing part: upload image to database
		String imageId = String.valueOf(image.getTag());
		user.patient.getBodyLocationImages().add(imageId);
	}

	public int checkBodyImageNumber(UserManager user)
	{
		BodyImages = user.patient.getBodyLocationImages();
		return BodyImages.size();
	}
	public void deleteBodyLocationImage(UserManager user, String ImageId)
	{
		//missing part: delete image from database
		BodyImages = user.patient.getBodyLocationImages();
		for(int i = 0; i<BodyImages.size();i++){
			if(BodyImages.get(i).equals(ImageId)){
				BodyImages.remove(i);
			}
		}
		user.patient.setBodyLocationImageIds(BodyImages);
	}
}
