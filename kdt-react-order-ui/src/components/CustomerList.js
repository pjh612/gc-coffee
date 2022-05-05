import {Customer} from "./Customer";

export function CustomerList({customers = [], setCustomers, onItemClick}) {
    const onRemove = id => {
        setCustomers(customers.filter(d => d.id !== id));
    };
    return <>
        <h5 className="flex-grow-0"><b>회원 목록</b></h5>
        <ul className="list-group appointments">
            {customers.map(v =>
                <li key={v.id} className="list-group-item d-flex mt-3">
                    <Customer onItemClick={onItemClick} {...v} onRemove={onRemove}/>
                </li>
            )}
        </ul>
    </>
}