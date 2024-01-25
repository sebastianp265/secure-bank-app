import React, {useEffect, useState} from "react";

interface PasswordInputProps {
    onPasswordChange: (password: string) => void
    passwordMask: string
}

export const PasswordInput = ({onPasswordChange, passwordMask}: PasswordInputProps) => {
    const [password, setPassword] = useState<string>(" ".repeat(passwordMask.length))
    const passwortPartsDisabled = passwordMask.split('').map(char => char != '+')

    const handlePasswordPartChange = (passwordPartId: number, newValue: string) => {
        const firstPart = password.substring(0, passwordPartId)
        newValue = newValue.length > 0 ? newValue : " "
        const secondPart = password.substring(passwordPartId + 1, password.length)
        console.log('firstPart: ', firstPart)
        console.log('newValue: ', newValue)
        console.log('secondPart: ', secondPart)
        console.log('password: ', firstPart + newValue + secondPart)
        setPassword(firstPart + newValue + secondPart)
    }

    useEffect(() => {
        onPasswordChange(password)
    }, [password, onPasswordChange])

    return (
        <div className="flex flex-row flex-wrap">
            {
                passwortPartsDisabled.map((isDisabled, index) => {
                    const inputProps = {
                        key: index,
                        onChange: isDisabled ? undefined : (e: React.ChangeEvent<HTMLInputElement>) =>
                            handlePasswordPartChange(index, e.target.value),
                        type: isDisabled ? "text" : "password",
                        maxLength: 1,
                        value: isDisabled ? " " : undefined,
                        disabled: isDisabled
                    }
                    return <div key={inputProps.key} className="flex flex-col">
                        <input {...inputProps} className={`m-1 ${isDisabled ? "bg-gray-300" :
                            "bg-[#D7B2F4]"} rounded-xl w-8 h-8 text-center`}/>
                        <span className="text-center">{inputProps.key + 1}</span>
                    </div>
                })
            }
        </div>
    )
}