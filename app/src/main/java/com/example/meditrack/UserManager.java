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
	private ApplicationManager.UserMode mode;

	//public UserManager() throws DataRepositorySingleton.InvalidUserMode, DataRepositorySingleton.DataRepositorySingletonNotInitialized {
	//	this.patient = mDRS.GetPatient();
	//	this.careProvider = mDRS.GetCareProvider();
	//}

	public void EditContactInfo(String email, String phoneNumber) throws DataRepositorySingleton.InvalidUserMode, DataRepositorySingleton.DataRepositorySingletonNotInitialized {

		if(mDRS.GetUserMode() == ApplicationManager.UserMode.CareGiver){
			throw new IllegalArgumentException(" Wrong user mode. ");
		}
		new_contactInfo.setEmail(email);
		new_contactInfo.setPhoneNumber(phoneNumber);
		mDRS.GetPatient().setContactInfo(new_contactInfo);
	}

	public void addPatient(String patientUserId) throws DataRepositorySingleton.InvalidUserMode, DataRepositorySingleton.DataRepositorySingletonNotInitialized {

		if(mDRS.GetUserMode() == ApplicationManager.UserMode.Patient){
			throw new IllegalArgumentException(" Wrong user mode. ");
		}
		patientIds = mDRS.GetCareProvider().getPatientIds();
		patientIds.add(patientUserId);
		mDRS.GetCareProvider().setPatientIds(patientIds);

	}


	public void addBodyLocationImage( ImageView image) throws DataRepositorySingleton.InvalidUserMode, DataRepositorySingleton.DataRepositorySingletonNotInitialized {
		//missing part: upload image to database
		if(mDRS.GetUserMode() == ApplicationManager.UserMode.CareGiver){
			throw new IllegalArgumentException(" Wrong user mode. ");
		}
		String imageId = String.valueOf(image.getTag());
		mDRS.GetPatient().getBodyLocationImages().add(imageId);
	}

	public int checkBodyImageNumber() throws DataRepositorySingleton.InvalidUserMode, DataRepositorySingleton.DataRepositorySingletonNotInitialized {
		BodyImages = mDRS.GetPatient().getBodyLocationImages();
		return BodyImages.size();
	}

	public void deleteBodyLocationImage(String ImageId) throws DataRepositorySingleton.InvalidUserMode, DataRepositorySingleton.DataRepositorySingletonNotInitialized {
		//missing part: delete image from database
		BodyImages = mDRS.GetPatient().getBodyLocationImages();
		if(BodyImages.size() == 0){
			throw new IllegalArgumentException("Empty list of body images,cannot do 'delete' ");
		}
		for(int i = 0; i<BodyImages.size();i++){
			if(BodyImages.get(i).equals(ImageId)){
				BodyImages.remove(i);
			}
		}
		mDRS.GetPatient().setBodyLocationImageIds(BodyImages);
	}
}
