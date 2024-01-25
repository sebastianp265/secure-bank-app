import {ContentBox} from "../../components/ContentBox.tsx";
import {useState} from "react";
import {PasswordInput} from "../../components/PasswordInput.tsx";
import {securityApi} from "../../api/securityApi.ts";
import {useNavigate} from "react-router-dom";
import {Button} from "../../components/Button.tsx";

export const Login = () => {
    const navigate = useNavigate()

    const [customerId, setCustomerId] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [passwordMask, setPasswordMask] = useState<string>("")
    const [isCustomerForm, setIsCustomerForm] = useState<boolean>(true)

    const [errorMessage, setErrorMessage] = useState<string>("")

    const handleNext = () => {
        securityApi.requestLogin(customerId)
            .then(response => {
                setPasswordMask(response.data.passwordMask)
                setIsCustomerForm(false)
                setErrorMessage("")
            })
            .catch(() => {
                if (customerId.length === 0) {
                    setErrorMessage("Please provide customer ID.")
                } else {
                    setErrorMessage("Something went wrong. Please try again later.")
                }
            })
    }

    const handleBack = () => {
        setIsCustomerForm(true)
        setErrorMessage("")
    }

    const handleLogin = () => {
        console.log(customerId, password)
        securityApi.login({customerId, password})
            .then(() => {
                setErrorMessage("")
                navigate("/home")
            })
            .catch(error => {
                setErrorMessage(error?.response?.data?.message)
            })
    }

    return (
        <ContentBox>
            {
                isCustomerForm ?
                    <div className="space-y-4 text-left ml-4 mr-4">
                        <h1 className="text-4xl font-bold">Bank App</h1>
                        <h2 className="text-2xl">Nice to see you!</h2>
                        <input defaultValue={customerId} onChange={(e) => setCustomerId(e.target.value)} type="text"
                               placeholder="Customer ID"
                               className="w-full p-2 pl-6 border-2 border-black rounded-2xl"/>
                        <span className="text-red-500 pt-2">{errorMessage}</span>
                        <div className="text-right">
                            <Button
                                    onClick={handleNext}>Next
                            </Button>
                        </div>
                    </div> :
                    <div className="space-y-4 text-left ml-4 mr-4">
                        <h1 className="text-4xl font-bold">Bank App</h1>
                        <h2 className="text-2xl">Provide highlighted characters of your password</h2>
                        <PasswordInput passwordMask={passwordMask} onPasswordChange={setPassword}/>
                        <span className="text-red-500 mt-3">{errorMessage}</span>
                        <div className="flex flex-row">
                            <Button onClick={handleBack}>Back</Button>
                            <div className="m-auto"></div>
                            <Button onClick={handleLogin}>Login</Button>
                        </div>
                    </div>
            }
        </ContentBox>
    )
}
