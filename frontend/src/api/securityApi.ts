import {customAxiosInstance} from "./axiosInstance.ts";

const axiosInstance = customAxiosInstance('')

export type LoginRequestDTO = {
    customerId: string,
    password: string
}

export type RequestLoginResponseDTO = {
    passwordMask: string
}

export const securityApi = {
    requestLogin(customerId: string) {
        return axiosInstance.post<RequestLoginResponseDTO>(`public/auth/request-login/${customerId}`)
    },
    login(loginRequest: LoginRequestDTO) {
        return axiosInstance.post<void>('public/auth/login', loginRequest)
    },
}