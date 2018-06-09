package CenterServerOrb;


/**
* CenterServerOrb/CenterServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CenterServerOrb.idl
* Thursday, June 7, 2018 9:53:52 o'clock PM EDT
*/

public interface CenterServerOperations 
{
  String createTRecord (String managerId, String firstName, String lastName, String address, String phone, String specialization, String location);
  String createSRecord (String managerId, String firstName, String lastName, String[] courseRegistered, String status, String statusDate);
  String getRecordCounts (String managerId);
  String editRecord (String managerId, String recordID, String fieldName, String newValue) throws CenterServerOrb.CenterServerPackage.except;
  void shutdown ();
} // interface CenterServerOperations
