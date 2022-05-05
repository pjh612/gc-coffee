import {Menu} from "./Menu";


export function MenuList({menus = [], setMenus, onItemClick}) {
    const onRemove = id => {
        setMenus(menus.filter(m => m.id !== id));
    };
    return <>
        <h5 className="flex-grow-0"><b>시술 목록</b></h5>
        <ul className="list-group menus">
            {menus.map(v =>
                <li key={v.id} className="list-group-item d-flex mt-3">
                    <Menu {...v} onItemClick={onItemClick} onRemove={onRemove}/>
                </li>
            )}
        </ul>
    </>
}