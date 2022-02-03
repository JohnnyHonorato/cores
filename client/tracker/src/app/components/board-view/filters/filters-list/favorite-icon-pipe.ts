import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'favoriteIcon'})
export class FavoriteIconPipe implements PipeTransform {
    transform(value): string {
        return value ? 'star' : 'star_outline';
    }
}
