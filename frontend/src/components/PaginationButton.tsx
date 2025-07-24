import React from 'react';

interface PaginationButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
}

const PaginationButton: React.FC<PaginationButtonProps> = ({ children, ...props }) => (
  <button
    className={
      'px-4 py-2 bg-blue-600 text-white rounded-lg font-semibold shadow hover:bg-blue-700 transition disabled:opacity-50 ' +
      (props.className || '')
    }
    {...props}
  >
    {children}
  </button>
);

export default PaginationButton; 