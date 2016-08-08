#include <stdio.h>
#include <sys/types.h>
#include <pthread.h>
#include <stdlib.h>
#include <semaphore.h>

int main(int argc, char* argv[]) {
	
	char clientId;
	char operater;
	char pageId[122];
	char contents[4096];
	
	//read requests
	FILE *fp;
  char ch;
	
    if((fp=fopen("all_requests.dat","rt"))==NULL){
        printf("\nCannot open file strike any key exit!");
        getch();
        exit(1);
    }
		else{
				
				
				fclose(fp);
		}
}