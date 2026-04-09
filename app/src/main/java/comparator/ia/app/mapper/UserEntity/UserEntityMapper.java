package comparator.ia.app.mapper.UserEntity;

import org.mapstruct.Mapper;

import comparator.ia.app.dtos.auth.register.DataRegister;
import comparator.ia.app.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
	
	UserEntity dataRegisterToUserEntity(DataRegister data);
	
	
}
