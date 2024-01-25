import {NavBar} from "../../components/NavBar.tsx";
import {useEffect, useState} from "react";
import {AccountGetDTO, CardPreviewGetDTO, domainApi} from "../../api/domainApi.ts";
import {useNavigate} from "react-router-dom";
import {ContentBox} from "../../components/ContentBox.tsx";
import {Button} from "../../components/Button.tsx";
import {AccountCard} from "./AccountCard.tsx";
import {InfoCard} from "../../components/InfoCard.tsx";
import {customAxiosInstance} from "../../api/axiosInstance.ts";

export const Home = () => {
    const navigate = useNavigate()

    const [accounts, setAccounts] = useState<AccountGetDTO[]>([])
    const [currentAccountIndex, setCurrentAccountIndex] = useState<number>(0)

    const [cards, setCards] = useState<CardPreviewGetDTO[]>([])

    useEffect(() => {
        domainApi.getAccounts()
            .then(response => {
                setAccounts(response.data)
            })
            .catch(() => {
                navigate("/")
            })

    }, [navigate])

    useEffect(() => {
        if (accounts.length > 0) {
            domainApi.getCardPreviews(accounts[currentAccountIndex].accountNumber)
                .then(response => {
                    setCards(response.data)
                })
                .catch(() => {
                    // navigate("/")
                })
        }
    }, [accounts, currentAccountIndex]);

    const axiosInstance = customAxiosInstance('')

    const handleShowDetails = (key: string, cardId: string) => {
        const endpoint = `private/cards/${accounts[currentAccountIndex].accountNumber}/details/${cardId}/`
            + key.toLowerCase().replace(" ", "-")
        axiosInstance.get(endpoint)
            .then(response => {
                let keyCamelCase = key.split(" ")
                    .map((keyPart, index) => {
                        if (index === 0) {
                            return keyPart.toLowerCase()
                        }
                        return keyPart.charAt(0).toUpperCase() + keyPart.slice(1).toLowerCase()
                    })
                    .join("")
                keyCamelCase = keyCamelCase.charAt(0).toLowerCase() + keyCamelCase.slice(1)
                setCards(cards.map(card => {

                    if (card.id === cardId) {
                        card = {
                            ...card,
                            [keyCamelCase]: String(response.data)
                        }
                    }
                    return card
                }))
            })
    }

    return (
        <div className="w-full h-full">
            <NavBar/>
            <ContentBox className="mt-4">
                <h2 className="font-bold">Accounts</h2>
                <ContentBox className="border-2 border-black w-full pr-2 pb-2 pt-3 space-y-2">
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
                <ContentBox className="flex flex-col space-y-2 w-full">
                    <span className="font-bold">Assigned cards</span>
                    {
                        cards.map(card => {
                            return (
                                <InfoCard key={card.id} content={
                                    [
                                        {
                                            key: "Card number",
                                            value: card.cardNumber,
                                            showDetails: (key: string) => {
                                                handleShowDetails(key, card.id)
                                            }
                                        },
                                        {
                                            key: "Valid thru",
                                            value: card.validThru,
                                            showDetails: (key: string) => {
                                                handleShowDetails(key, card.id)
                                            }
                                        },
                                        {
                                            key: "CVV Code",
                                            value: card.cvvCode,
                                            showDetails: (key: string) => {
                                                handleShowDetails(key, card.id)
                                            }
                                        }
                                    ]
                                }/>
                            )
                        })
                    }
                </ContentBox>
            </ContentBox>
        </div>
    )
}