#include <stdio.h>
#include <sys/types.h>
#include <pthread.h>
#include <stdlib.h>
#include <semaphore.h>

void *server(void *arg){
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

	pthread_exit(NULL);
  return (NULL);
}

void *client(void *arg){
	

	
	pthread_exit(NULL);
  return (NULL);
}

int main(int argc, char* argv[]) {
		int clientNo = argv[1] ;		
		pthread_t *thread;
		pthread_t *threads;
		
		// initializes server thread
		pthread_create(&thread, NULL, server, (void*)1);
		
		// initializes client threads
		for (i=0; i<clientNo; i++)
    {
				pthread_create(&threads[i], NULL, client, (void*)i); 
    }

		for (i=0; i<clientNo; i++)
    {
			pthread_join(threads[i], NULL);
		}
}