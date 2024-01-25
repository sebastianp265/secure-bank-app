import {useNavigate, useParams} from "react-router-dom";
import {domainApi, TransferGetDTO} from "../../../api/domainApi.ts";
import {useEffect, useState} from "react";
import {NavBar} from "../../../components/NavBar.tsx";
import {ContentBox} from "../../../components/ContentBox.tsx";

export const TransferHistory = () => {
    const {accountNumber} = useParams()
    const [transfers, setTransfers] = useState<TransferGetDTO[]>([])
    const navigate = useNavigate()

    useEffect(() => {
        if (accountNumber != undefined) {
            domainApi.getHistoryOfTransfers(accountNumber)
                .then(response => {
                    setTransfers(response.data)
                })
                .catch(() => {
                    navigate("/")
                })
        }
    }, [accountNumber, navigate])

    return (
        <div className="w-full h-full">
            <NavBar/>
            <ContentBox className="mt-6 w-fit">
                <h1 className="font-bold mb-2">History of transfers</h1>
                <div className="flex flex-col justify-center items-center space-y-2">
                    {
                        transfers.map(transfer => {
                            const {accountNumber, amount, sent, madeAt, title} = transfer
                            return (
                                <div key={transfer.madeAt} className="flex flex-row justify-between items-center
                                    border-2 border-black w-full p-2 rounded-2xl ">
                                    <div className="flex flex-col justify-center items-start">
                                        <span className="font-bold">{sent ? "Send to:" : "Received from"} {accountNumber}</span>
                                        <span>Title: {title}</span>
                                    </div>
                                    <div className="flex flex-col justify-center items-end">
                                        <span className="font-bold">{amount} €</span>
                                        <span>Sent at: {madeAt}</span>
                                    </div>
                                </div>
                            )
                        })
                    }
                </div>
            </ContentBox>
        </div>
    )
}