import React from "react";

interface ButtonProps {
    onClick?: React.MouseEventHandler<HTMLButtonElement>
    children: React.ReactNode
    type?: "submit"
    className?: string
}

export const Button = ({onClick, children, type, className}: ButtonProps) => {
    return (
        <button type={type} className={`${className} p-2 pl-4 pr-4 border-2 border-black rounded-2xl`}
                onClick={onClick}>{children}
        </button>
    )
}