################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../engineadapter_EngineAdapter.cpp 

OBJS += \
./engineadapter_EngineAdapter.o 

CPP_DEPS += \
./engineadapter_EngineAdapter.d 


# Each subdirectory must supply rules for building sources it contributes
%.o: ../%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cross G++ Compiler'
	g++ -I/usr/lib64/jvm/java-8-jdk/include -I/usr/lib64/jvm/java-8-jdk/include/linux -O0 -g3 -Wall -c -fmessage-length=0 -fPIC -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


