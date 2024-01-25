import React from "react";

interface ContentBoxProps {
    children: React.ReactNode;
    className?: string;
}

export const ContentBox = ({children, className}: ContentBoxProps) => {
    return (
        <div className={`m-auto w-[60%] bg-white p-6 rounded-2xl ${className}`}>
            {children}
        </div>
    )
}