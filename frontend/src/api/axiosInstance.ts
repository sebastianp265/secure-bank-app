import axios, {AxiosError} from "axios";

export const customAxiosInstance = (mapping: string) => {
    const client = axios.create({
        baseURL: "http://localhost:8080/" + mapping,
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json'
        },
        withCredentials: true
    });

    client.interceptors.request.use(request => {
        console.debug("Making request:")
        console.debug(request)
        return request
    })

    client.interceptors.response.use(
        response=> {
            console.debug("Got response: ")
            console.debug(response)
            return response;
        },
        (error: AxiosError) => {
            if(error.request) {
                console.error(error.request)
            } else {
                console.error("Request was not made: ", error.message)
            }
            return Promise.reject(error)
        }
    )

    return client
}