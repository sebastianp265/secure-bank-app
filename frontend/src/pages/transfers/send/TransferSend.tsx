import {useEffect, useState} from "react";
import {domainApi, TransferRequestDTO} from "../../../api/domainApi.ts";
import {useNavigate} from "react-router-dom";
import {ContentBox} from "../../../components/ContentBox.tsx";
import {useForm} from "react-hook-form";
import {NavBar} from "../../../components/NavBar.tsx";
import {Button} from "../../../components/Button.tsx";


export const TransferSend = () => {
    const [accountNumbers, setAccountNumbers] = useState<string[]>([])
    const navigate = useNavigate()

    const {
        register,
        handleSubmit,
        formState: {errors}
    } = useForm<TransferRequestDTO>()

    useEffect(() => {
        domainApi.getAccounts()
            .then(response => {
                setAccountNumbers(response.data.map(account => account.accountNumber))
                navigate("/home")
            })
            .catch(() => {
                navigate("/")
            })
    }, [navigate])

    const onSubmit = (data: TransferRequestDTO) => {
        console.log(data)
        domainApi.sendTransfer(data)
            .then(() => {

            })
            .catch(() => {

            })
    }

    const isAccountNumberValid = (accountNumber: string) => {
        accountNumber = accountNumber.replace(/\s/g, '')
        return accountNumber.length === 26
            && RegExp(/^\d+$/).exec(accountNumber) !== null
    }

    return (
        <div className="w-full h-full">
            <NavBar/>
            <ContentBox className="m-auto mt-8">
                <form onSubmit={handleSubmit(onSubmit)}
                      className="flex flex-col justify-center
                            items-start
                            [&>h2]:m-1 [&>input]:border-2 [&>input]:border-black [&>input]:rounded-2xl [&>input]:p-2 [&>input]:pl-6 [&>input]:w-full

                               ">

                    <h2>From account</h2>
                    <select className="w-full p-2 pl-6 border-2 border-black rounded-2xl">
                        {
                            accountNumbers.map(accountNumber => {
                                return (
                                    <option key={accountNumber} value={accountNumber}
                                            {...register("fromAccount", {required: true})}>{accountNumber}</option>
                                )
                            })
                        }
                    </select>

                    <h2>Recipient full name</h2>
                    <input type="text" {...register("recipientFullName", {required: true})}/>
                    {errors.recipientFullName && <span className="text-red-500">This field is required</span>}

                    <h2>Recipient account number</h2>
                    <input type="text" {...register("recipientAccountNumber", {required: true, validate: isAccountNumberValid})}/>
                    {errors.recipientAccountNumber && <span className="text-red-500">Account number must be 26 digits long</span>}

                    <h2>Title</h2>
                    <input type="text" {...register("title", {required: true})}/>
                    {errors.title && <span className="text-red-500">Title is required</span>}

                    <h2>Amount</h2>
                    <input type="text" {...register("amount", {required: true, pattern: /^\d+(.\d{1,2})?$/})}/>
                    {errors.amount && <span className="text-red-500">Amount must be valid</span>}

                    <Button type="submit" className="ml-auto mt-3">Send</Button>
                </form>
            </ContentBox>
        </div>
    )
}