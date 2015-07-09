#include <jni.h>
#include <stdio.h>

#include <stdlib.h>
#include <iostream>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>
#include "engineadpater_EngineAdapter.h"

/////////////////////////////////////////////////////////

#define NUM_PIPES          2

#define PARENT_WRITE_PIPE  0
#define PARENT_READ_PIPE   1

int pipes[NUM_PIPES][2];
int outfd[2];
int infd[2];

/* always in a pipe[], pipe[0] is for read and
   pipe[1] is for write */
#define READ_FD  0
#define WRITE_FD 1

#define PARENT_READ_FD  ( pipes[PARENT_READ_PIPE][READ_FD]   )
#define PARENT_WRITE_FD ( pipes[PARENT_WRITE_PIPE][WRITE_FD] )

#define CHILD_READ_FD   ( pipes[PARENT_WRITE_PIPE][READ_FD]  )
#define CHILD_WRITE_FD  ( pipes[PARENT_READ_PIPE][WRITE_FD]  )

/////////////////////////////////////////////////////////


JNIEXPORT jstring JNICALL Java_engineadapter_EngineAdapter_start
  (JNIEnv *env, jobject obj, jstring parameter) {

  //get String param
  const char *param = env->GetStringUTFChars(parameter, JNI_FALSE);
  jstring result;

  int sysRes = 0;
  int writeRes = 0;
  ////////////////////////////////////////////////////////////////

  char buffer[1024];

  pipe(pipes[PARENT_READ_PIPE]);
  pipe(pipes[PARENT_WRITE_PIPE]);

  if(!fork()) {
  	  dup2(CHILD_READ_FD, STDIN_FILENO);
  	  dup2(CHILD_WRITE_FD, STDOUT_FILENO);

  	  close(CHILD_READ_FD);
  	  close(CHILD_WRITE_FD);
  	  close(PARENT_READ_FD);
  	  close(PARENT_WRITE_FD);

  	  sysRes = system(param);
  } else {
	  int count;

	  close(CHILD_READ_FD);
	  close(CHILD_WRITE_FD);

	  count = read(PARENT_READ_FD, buffer, sizeof(buffer)-1);
	  if (count >= 0) {
		  buffer[count] = 0;
		  result = env->NewStringUTF(buffer);
	  } else {
		  result = env->NewStringUTF("error");
		  if(sysRes == -1)
			  result = env->NewStringUTF("error: system return -1");
		  if(writeRes < 0)
			  result = env->NewStringUTF("error: write result < 0");
	  }
  }

  ////////////////////////////////////////////////////////////////

  env->ReleaseStringUTFChars(parameter, param);

  return result;
}

JNIEXPORT jboolean JNICALL Java_engineadapter_EngineAdapter_stop
  (JNIEnv *env, jobject obj) {

  jboolean f = true;

  return f;
}

JNIEXPORT jboolean JNICALL Java_engineadapter_EngineAdapter_write
  (JNIEnv *env, jobject obj, jstring parameter) {

	const char *param = env->GetStringUTFChars(parameter, JNI_FALSE);

	int written = 0;
	written = write(PARENT_WRITE_FD, param, strlen(param));

    env->ReleaseStringUTFChars(parameter, param);
    return (written > 0);
}

JNIEXPORT jstring JNICALL Java_engineadapter_EngineAdapter_read
	(JNIEnv *env, jobject obj){

	jstring result = env->NewStringUTF("none");

	char buffer[3000] = {0};
	int count;

	count = read(PARENT_READ_FD, buffer, sizeof(buffer));
	if (count >= 0) {
		buffer[count] = 0;
		result = env->NewStringUTF(buffer);
	} else {
		result = env->NewStringUTF("An error occurred.");
	}

	return result;
}
