//package br.com.shapeup.factories;
//
//import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
//
//import java.util.UUID;
//
//public class UserEntityFactory {
//    private static UserEntityFactory instace = null;
//
//    public static UserEntityFactory getInstance() {
//        if (instace == null) {
//            instace = new UserEntityFactory();
//        }
//        return instace;
//    }
//
//    public UserEntity create() {
//        var user = UserFactory.getInstance().create();
//        return new UserEntity(
//                UUID.randomUUID(),
//                user.getName(),
//                user.getLastName(),
//                user.getUsername(),
//                user.getEmail().getValue(),
//                user.getCellPhone().getValue(),
//                user.getPassword().getValue(),
//                user.getBirth().getValue(),
//                "ADMIN"
//        );
//    }
//}
