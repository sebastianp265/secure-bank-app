import React from "react";

interface ContentBoxProps {
    children: React.ReactNode;
}

export const ContentBox = ({children}: ContentBoxProps) => {
    return (
        <div className="m-auto mt-[25%] w-[60%] bg-white p-6 rounded-2xl">
            {children}
        </div>
    )
}