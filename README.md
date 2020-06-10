# RMI Client

## How to launch

1. Install [Maven](https://maven.apache.org/install.html)
2. Build JAR 
    ```shell script
    mvn package
    ```
3. Run the JAR. You can pass the server name and port as command line arguments or leave them default
    ```shell script
    java -cp target\Client-1.0-SNAPSHOT.jar ua.edu.lpnu.dsct.client.Main [<server_name> <port>]
    ```

## How to use

```shell script
----------> PING <---------- 
Sends an empty message to the server to test the connection.
Note: no other symbols are allowed after the command.
----------> ECHO <---------- 
Sends selected text to the server and returns it back.
Example: echo 'Hello world!'
----------> GENERATE <---------- 
Sends request to server to generate numbers (whole or decimal) and saves the generated numbers to file.
Template: generate "path/to/file" <amount of numbers to generate> <type: decimal or whole> <min> <max>
Example: generate "generated.txt" 100 whole 0 1000
(This will generate 100 whole numbers from 0 to 1000 and put them into file named 'generated.txt')
Note: quotes (") around file path are mandatory.
----------> PROCESS <---------- 
Reads the file, sends its content to server. Server sorts the numbers and sends the result, which is written to output file.
Template: process "path/to/input/file" "path/to/output/file"
Example: process "to_sort.txt" "sorted.txt"
Note: quotes (") around file paths are mandatory.
----------> HELP <----------
Print out helpful message on how to use the program.
Use 'help <command>' to get info about specific command 
```