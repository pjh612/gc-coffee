import {Designer} from "./Designer";

export function DesignerList({designers=[], setDesigners, onItemClick}) {
    const onRemove = id => {
        setDesigners(designers.filter(d => d.id !== id));
    };
    return <>
        <h5 className="flex-grow-0"><b>디자이너 목록</b></h5>
        <ul className="list-group designers">
            {designers.map(v =>
                <li key={v.id} className="list-group-item d-flex mt-3">
                    <Designer {...v} onRemove={onRemove} onItemClick={onItemClick}/>
                </li>
            )}
        </ul>
    </>
}