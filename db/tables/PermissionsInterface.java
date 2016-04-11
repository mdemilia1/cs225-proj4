import java.sql.*;
public class PermissionsInterface {
enum Who {
  SELF,
  OTHER,
}

enum Operation {
   VIEW,
   WRITE,
   CREATE,
   DELETE,
}

enum Level {
   ADMIN,
   COMMITTEE_LEADER,
   COMMITTEE_MEMBER,
   PARTICIPANT,
}
public boolean hasPermission(String table, String field, Operation operation, Who who, Level otherLevel) {
       return true;
}

public boolean hasPermission(String table, String field, Operation operation) {
      return true;
}

public boolean hasPermission(String table, String field, Operation operation, int userID) {
      return true;
}



}
