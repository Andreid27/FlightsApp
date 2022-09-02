package org.company.Models;

public enum UserRole {
    ADMIN("admin"),MANAGER("manager"),USER("user");

    private final String userRole;
    private UserRole(String userRole){
        this.userRole=userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public static boolean UserRoleIsValid(String userRole){
        boolean isValid=false;
        if(userRole.equals(UserRole.ADMIN.getUserRole()) || userRole.equals(UserRole.MANAGER.getUserRole())||userRole.equals(UserRole.USER.getUserRole())){
            isValid=true;
        }
        return isValid;
    }
}
