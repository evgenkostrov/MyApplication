package com.example.myapplication.personlist


import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.load
import com.example.myapplication.Person
import com.example.myapplication.R
import com.example.myapplication.databinding.MainFragmentBinding
import com.example.myapplication.ui.main.MainFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PersonListFragment : Fragment() {

    companion object {
        fun newInstance() = PersonListFragment()
    }

    private val viewModel by lazy { ViewModelProvider(this).get(PersonListViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return ComposeView(requireContext()).apply {
            setContent {
                PersonList(viewModel = viewModel, onPersonClick = ::showPersonInfo)

            }
        }
    }

    private fun showPersonInfo(person: Person) {

        val  fragment = MainFragment.newInstance(person.id)
        requireActivity().supportFragmentManager.beginTransaction()

            .add(R.id.container, fragment)
            .commitNow()
//        Toast.makeText(requireContext(), person.name, Toast.LENGTH_SHORT).show()

    }

//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(this).get(PersonListViewModel::class.java)
//        viewModel.person.onEach { person -> if(person!=null) displayPerson(person) }.launchIn(lifecycleScope)
//    }
}

@Composable
fun PersonList(
    viewModel: PersonListViewModel,
    onPersonClick: (Person) -> Unit
) {
    val pagedData = object : PagingSource<String, Person>() {
        override fun getRefreshKey(state: PagingState<String, Person>): String? =
            viewModel.startPage

        override suspend fun load(params: LoadParams<String>): LoadResult<String, Person> {
            val pageInfo = viewModel.loadPersonPage(params.key ?: viewModel.startPage)
            return LoadResult.Page(
                data = pageInfo.results,
                nextKey = pageInfo.info.next,
                prevKey = pageInfo.info.prev,
            )
        }

    }
    val pager = Pager(PagingConfig(pageSize = 20)) { pagedData }.flow.collectAsLazyPagingItems()

    LazyColumn {
        items(pager) {
            it?.let {
                Person(person = it, onPersonClick = onPersonClick)
            }
        }
    }
}

@Composable
fun Person(
    person: Person,
    onPersonClick: (Person) -> Unit
) {
    Row(modifier = Modifier.padding(6.dp).clickable { onPersonClick(person) }) {
        Image(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp),
            painter = rememberImagePainter(data = person.image),
            contentDescription = null
        )
        Column(modifier = Modifier.padding(horizontal = 6.dp)) {
            Text(text = person.name)
            Text(text = person.gender.name)
        }


    }

}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun PersonSample() {
    Person(person = Person(
        id = 3,
        name = "Hello",
        status = "Online",
        gender = Person.Gender.Male,
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    ),
        onPersonClick = {

        }
    )
}
