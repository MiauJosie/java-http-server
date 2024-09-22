public class RequestLine {

    Method method;
    String path;
    Version version;

    public static RequestLine parseRequestLine(String inputLine) {
        String[] arr = inputLine.split(" ");

        if (arr.length != 3) {
            throw new RuntimeException();
        }

        String method = arr[0];
        String path = arr[1];
        String version = arr[2];

        Method parsedMethod = Method.NULL;
        Version parsedVersion = Version.NULL;
        String parsedPath = path;

        if (version.equals("HTTP/1.1")) {
            parsedVersion = Version.HTTP_1_1;
        }

        if (method.equals("GET")) {
            parsedMethod = Method.GET;
        } else if (method == "POST") {
            parsedMethod = Method.POST;
        }

        return new RequestLine(parsedMethod, parsedPath, parsedVersion);
    }

    public RequestLine(Method parsedMethod, String parsedPath, Version parsedVersion) {
        method = parsedMethod;
        path = parsedPath;
        version = parsedVersion;
    }
}
