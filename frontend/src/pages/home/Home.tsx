import {NavBar} from "../../components/NavBar.tsx";
import {useEffect, useState} from "react";
import {AccountGetDTO, domainApi} from "../../api/domainApi.ts";
import {useNavigate} from "react-router-dom";
import {ContentBox} from "../../components/ContentBox.tsx";
import {Button} from "../../components/Button.tsx";
import {AccountCard} from "./AccountCard.tsx";

export const Home = () => {
    const navigate = useNavigate()

    const [accounts, setAccounts] = useState<AccountGetDTO[]>([])
    const [currentAccountIndex, setCurrentAccountIndex] = useState<number>(0)

    useEffect(() => {
        domainApi.getAccounts()
            .then(response => {
                console.log('success')
                setAccounts(response.data)
            })
            .catch(() => {
                navigate("/")
            })
    }, [navigate])

    return (
        <div className="w-full h-full">
            <NavBar/>
            <ContentBox className="mt-4">
                <h2 className="font-bold">Accounts</h2>
                <ContentBox className="border-2 border-black w-full pr-2 pb-2 pt-3 space-y-2" >
                    {
                        currentAccountIndex > 0 &&
                        <Button onClick={() => setCurrentAccountIndex(currentAccountIndex - 1)}>Previous</Button>
                    }
                    {
                        accounts.length > 0 &&
                        <AccountCard account={accounts[currentAccountIndex]}/>
                    }
                    {
                        currentAccountIndex < accounts.length - 1 &&
                        <Button onClick={() => setCurrentAccountIndex(currentAccountIndex + 1)}>Next</Button>
                    }
                </ContentBox>
            </ContentBox>
        </div>
    )
}