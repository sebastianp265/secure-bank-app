import {customAxiosInstance} from "./axiosInstance.ts";

const axiosInstance = customAxiosInstance('private/')

export type AccountGetDTO = {
    accountNumber: string,
    balance: number
}

export type TransferRequestDTO = {
    fromAccount: string,
    recipientFullName: string,
    recipientAccountNumber:string,
    title: string,
    amount: number
}

export type TransferGetDTO = {
    sent: boolean,
    madeAt: string,
    title: string,
    amount: number,
    accountNumber: string
}

export type CardPreviewGetDTO = {
    id: string,
    cardNumber: string
    cvvCode: string,
    validThru: string,
}

export const domainApi = {
    getAccounts() {
        return axiosInstance.get<AccountGetDTO[]>('accounts')
    },

    getHistoryOfTransfers(accountNumber: string) {
        return axiosInstance.get<TransferGetDTO[]>(`transfers/history/${accountNumber}`)
    },

    sendTransfer(transferRequestDTO: TransferRequestDTO) {
        return axiosInstance.post('transfers', transferRequestDTO)
    },

    getCardPreviews(accountNumber: string) {
        return axiosInstance.get<CardPreviewGetDTO[]>(`cards/${accountNumber}`)
    },
}