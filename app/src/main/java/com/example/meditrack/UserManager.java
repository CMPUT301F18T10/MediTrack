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
		DataRepositorySingleton.GetPatient().setContactInfo(new_contactInfo);
	}

	public void addPatient(String patientUserId)
	{
		patientIds = DataRepositorySingleton.GetCareProvider().getPatientIds();
		patientIds.add(patientUserId);
		DataRepositorySingleton.GetCareProvider().setPatientIds(patientIds);

	}


	public void addBodyLocationImage(ImageView image)
	{
		//missing part: upload image to database
		String imageId = String.valueOf(image.getTag());
		DataRepositorySingleton.GetPatient().getBodyLocationImages().add(imageId);
	}

	public int checkBodyImageNumber()
	{
		BodyImages = DataRepositorySingleton.GetPatient().getBodyLocationImages();
		return BodyImages.size();
	}
	public void deleteBodyLocationImage(String ImageId)
	{
		//missing part: delete image from database
		BodyImages = DataRepositorySingleton.GetPatient().getBodyLocationImages();
		for(int i = 0; i<BodyImages.size();i++){
			if(BodyImages.get(i).equals(ImageId)){
				BodyImages.remove(i);
			}
		}
		DataRepositorySingleton.GetPatient().setBodyLocationImageIds(BodyImages);
	}
}
